package me.zhengjie.res;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
@RestController
@RequestMapping("/api/serverMonitor")
public class ServerMonitorController {


    private static final double GB = 1024 * 1024 * 1024.00;

    @GetMapping
    public ResponseEntity<Object> getServerInfo() {
        Map<String, Object> resultMap = new HashMap<>(8);
        try {
            SystemInfo si = new SystemInfo();
            HardwareAbstractionLayer hal = si.getHardware();
            CentralProcessor processor = hal.getProcessor();
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            TimeUnit.SECONDS.sleep(1);
            resultMap.put("cpuRate", processor.getSystemCpuLoadBetweenTicks(prevTicks));
            resultMap.put("cpuCore", processor.getLogicalProcessorCount());
            GlobalMemory memory = hal.getMemory();
            resultMap.put("memTotal", memory.getTotal() / GB);
            resultMap.put("memUsed", (memory.getTotal() - memory.getAvailable()) / GB);
            double diskTotal = 0;
            double diskUsable = 0;
            OperatingSystem os = si.getOperatingSystem();
            OSFileStore[] fsArray = os.getFileSystem().getFileStores();
            for (OSFileStore fs : fsArray) {
                long usable = fs.getUsableSpace();
                long total = fs.getTotalSpace();
                diskTotal += total / GB;
                diskUsable += usable / GB;
            }
            resultMap.put("diskTotal", diskTotal);
            resultMap.put("diskUsed", diskTotal - diskUsable);
            VirtualMemory vm = memory.getVirtualMemory();
            resultMap.put("swapTotal", vm.getSwapTotal() / GB);
            resultMap.put("swapUsed", vm.getSwapUsed() / GB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
