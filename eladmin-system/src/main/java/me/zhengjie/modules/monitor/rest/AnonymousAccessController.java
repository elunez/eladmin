package me.zhengjie.modules.monitor.rest;

import me.zhengjie.modules.security.annotation.AnonymousAccess;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 匿名访问测试类
 */
@RestController
@RequestMapping("api")
public class AnonymousAccessController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();


    @GetMapping("/anonymousAccess1")
    @AnonymousAccess
    public int testAnonymousAccess1() {
        return ATOMIC_INTEGER.incrementAndGet();
    }

    @GetMapping("/anonymousAccess2")
    @AnonymousAccess
    @PreAuthorize("hasAnyRole('ROLE_ANONYMOUS')")
    public int testAnonymousAccess2() {
        return ATOMIC_INTEGER.incrementAndGet();
    }

    @GetMapping("/anonymousAccess3")
    @PreAuthorize("hasAnyRole('ROLE_ANONYMOUS')")
    public int testAnonymousAccess3() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
