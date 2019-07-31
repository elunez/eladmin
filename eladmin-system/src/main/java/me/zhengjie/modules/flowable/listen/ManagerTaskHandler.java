package me.zhengjie.modules.flowable.listen;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @Author: fumin
 * @Description:
 * @Date: Create in 2019/5/20 9:40
 */
public class ManagerTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("-----------------------经理执行----------------");
        delegateTask.setAssignee("经理");
    }
}
