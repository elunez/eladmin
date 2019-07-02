package me.zhengjie.rest;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.AlipayConfig;
import me.zhengjie.domain.vo.TradeVo;
import me.zhengjie.service.AlipayService;
import me.zhengjie.utils.AliPayStatusEnum;
import me.zhengjie.utils.AlipayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AliPayController {

    @Autowired
    AlipayUtils alipayUtils;

    @Autowired
    private AlipayService alipayService;

    @GetMapping(value = "/aliPay")
    public ResponseEntity<AlipayConfig> get(){
        return new ResponseEntity<>(alipayService.find(),HttpStatus.OK);
    }

    @Log("配置支付宝")
    @PutMapping(value = "/aliPay")
    public ResponseEntity payConfig(@Validated @RequestBody AlipayConfig alipayConfig){
        alipayConfig.setId(1L);
        alipayService.update(alipayConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("支付宝PC网页支付")
    @ApiOperation(value = "PC网页支付")
    @PostMapping(value = "/aliPay/toPayAsPC")
    public ResponseEntity<String> toPayAsPC(@Validated@RequestBody TradeVo trade) throws Exception{
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPC(alipay,trade);
        return ResponseEntity.ok(payUrl);
    }

    @Log("支付宝手机网页支付")
    @ApiOperation(value = "手机网页支付")
    @PostMapping(value = "/aliPay/toPayAsWeb")
    public ResponseEntity<String> toPayAsWeb(@Validated @RequestBody TradeVo trade) throws Exception{
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsWeb(alipay,trade);
        return ResponseEntity.ok(payUrl);
    }

    @ApiIgnore
    @GetMapping("/aliPay/return")
    @ApiOperation(value = "支付之后跳转的链接")
    public ResponseEntity<String> returnPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayConfig alipay = alipayService.find();
        response.setContentType("text/html;charset=" + alipay.getCharset());
        //内容验签，防止黑客篡改参数
        if(alipayUtils.rsaCheck(request,alipay)){
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            System.out.println("商户订单号"+outTradeNo+"  "+"第三方交易号"+tradeNo);

            /**
             * 根据业务需要返回数据，这里统一返回OK
             */
            return new ResponseEntity<>("payment successful",HttpStatus.OK);
        }else{
            /**
             * 根据业务需要返回数据
             */
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiIgnore
    @RequestMapping("/aliPay/notify")
    @ApiOperation(value = "支付异步通知(要公网访问)，接收异步通知，检查通知内容app_id、out_trade_no、total_amount是否与请求中的一致，根据trade_status进行后续业务处理")
    public ResponseEntity notify(HttpServletRequest request) throws Exception{
        AlipayConfig alipay = alipayService.find();
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder("/****************************** pay notify ******************************/\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n") );
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request,alipay)) {
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //验证
            if(tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue())||tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())){
                /**
                 * 验证通过后应该根据业务需要处理订单
                 */
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
