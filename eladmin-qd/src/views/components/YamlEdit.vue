<template>
  <div class="app-container">
    <p class="warn-content">
      Yaml编辑器 基于
      <a href="https://github.com/codemirror/CodeMirror" target="_blank">CodeMirror</a>，
      主题预览地址 <a href="https://codemirror.net/demo/theme.html#idea" target="_blank">Theme</a>
    </p>
    <Yaml :value="value" :height="height"/>
  </div>
</template>

<script>
import Yaml from '@/components/YamlEdit/index'
export default {
  name: 'YamlEdit',
  components: { Yaml },
  data() {
    return {
      height: document.documentElement.clientHeight - 210 + 'px',
      value: '# 展示数据，如需更换主题，请在src/components/YamlEdit 目录中搜索原主题名称进行替换\n' +
        '\n' +
        '# ===================================================================\n' +
        '# Spring Boot configuration.\n' +
        '#\n' +
        '# This configuration will be overridden by the Spring profile you use,\n' +
        '# for example application-dev.yml if you use the "dev" profile.\n' +
        '#\n' +
        '# More information on profiles: https://www.jhipster.tech/profiles/\n' +
        '# More information on configuration properties: https://www.jhipster.tech/common-application-properties/\n' +
        '# ===================================================================\n' +
        '\n' +
        '# ===================================================================\n' +
        '# Standard Spring Boot properties.\n' +
        '# Full reference is available at:\n' +
        '# http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html\n' +
        '# ===================================================================\n' +
        '\n' +
        'eureka:\n' +
        '    client:\n' +
        '        enabled: true\n' +
        '        healthcheck:\n' +
        '            enabled: true\n' +
        '        fetch-registry: true\n' +
        '        register-with-eureka: true\n' +
        '        instance-info-replication-interval-seconds: 10\n' +
        '        registry-fetch-interval-seconds: 10\n' +
        '    instance:\n' +
        '        appname: product\n' +
        '        instanceId: product:${spring.application.instance-id:${random.value}}\n' +
        '        #instanceId: 127.0.0.1:9080\n' +
        '        lease-renewal-interval-in-seconds: 5\n' +
        '        lease-expiration-duration-in-seconds: 10\n' +
        '        status-page-url-path: ${management.endpoints.web.base-path}/info\n' +
        '        health-check-url-path: ${management.endpoints.web.base-path}/health\n' +
        '        metadata-map:\n' +
        '            zone: primary # This is needed for the load balancer\n' +
        '            profile: ${spring.profiles.active}\n' +
        '            version: ${info.project.version:}\n' +
        '            git-version: ${git.commit.id.describe:}\n' +
        '            git-commit: ${git.commit.id.abbrev:}\n' +
        '            git-branch: ${git.branch:}\n' +
        'ribbon:\n' +
        '    ReadTimeout: 120000\n' +
        '    ConnectTimeout: 300000\n' +
        '    eureka:\n' +
        '        enabled: true\n' +
        'zuul:\n' +
        '  host:\n' +
        '    connect-timeout-millis: 5000\n' +
        '    max-per-route-connections: 10000\n' +
        '    max-total-connections: 5000\n' +
        '    socket-timeout-millis: 60000\n' +
        '  semaphore:\n' +
        '    max-semaphores: 500\n' +
        '\n' +
        'feign:\n' +
        '    hystrix:\n' +
        '        enabled: true\n' +
        '    client:\n' +
        '        config:\n' +
        '            default:\n' +
        '                connectTimeout: 500000\n' +
        '                readTimeout: 500000\n' +
        '\n' +
        '# See https://github.com/Netflix/Hystrix/wiki/Configuration\n' +
        'hystrix:\n' +
        '    command:\n' +
        '        default:\n' +
        '            circuitBreaker:\n' +
        '                sleepWindowInMilliseconds: 100000\n' +
        '                forceClosed: true\n' +
        '            execution:\n' +
        '                isolation:\n' +
        '#                    strategy: SEMAPHORE\n' +
        '# See https://github.com/spring-cloud/spring-cloud-netflix/issues/1330\n' +
        '                    thread:\n' +
        '                        timeoutInMilliseconds: 60000\n' +
        '    shareSecurityContext: true\n' +
        '\n' +
        'management:\n' +
        '    endpoints:\n' +
        '        web:\n' +
        '            base-path: /management\n' +
        '            exposure:\n' +
        '                include: ["configprops", "env", "health", "info", "threaddump"]\n' +
        '    endpoint:\n' +
        '        health:\n' +
        '            show-details: when_authorized\n' +
        '    info:\n' +
        '        git:\n' +
        '            mode: full\n' +
        '    health:\n' +
        '        mail:\n' +
        '            enabled: false # When using the MailService, configure an SMTP server and set this to true\n' +
        '    metrics:\n' +
        '        enabled: false # http://micrometer.io/ is disabled by default, as we use http://metrics.dropwizard.io/ instead\n' +
        '\n' +
        'spring:\n' +
        '    application:\n' +
        '        name: product\n' +
        '    jpa:\n' +
        '        open-in-view: false\n' +
        '        hibernate:\n' +
        '            ddl-auto: update\n' +
        '            naming:\n' +
        '                physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy\n' +
        '                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy\n' +
        '    messages:\n' +
        '        basename: i18n/messages\n' +
        '    mvc:\n' +
        '        favicon:\n' +
        '            enabled: false\n' +
        '    thymeleaf:\n' +
        '        mode: HTML\n' +
        'security:\n' +
        '    oauth2:\n' +
        '        resource:\n' +
        '            filter-order: 3\n' +
        '\n' +
        'server:\n' +
        '    servlet:\n' +
        '        session:\n' +
        '            cookie:\n' +
        '                http-only: true\n' +
        '\n' +
        '# Properties to be exposed on the /info management endpoint\n' +
        'info:\n' +
        '    # Comma separated list of profiles that will trigger the ribbon to show\n' +
        '    display-ribbon-on-profiles: "dev"\n' +
        '\n' +
        '# ===================================================================\n' +
        '# JHipster specific properties\n' +
        '#\n' +
        '# Full reference is available at: https://www.jhipster.tech/common-application-properties/\n' +
        '# ===================================================================\n' +
        '\n' +
        'jhipster:\n' +
        '    async:\n' +
        '        core-pool-size: 2\n' +
        '        max-pool-size: 50\n' +
        '        queue-capacity: 10000\n' +
        '    # By default CORS is disabled. Uncomment to enable.\n' +
        '    #cors:\n' +
        '        #allowed-origins: "*"\n' +
        '        #allowed-methods: "*"\n' +
        '        #allowed-headers: "*"\n' +
        '        #exposed-headers: "Authorization,Link,X-Total-Count"\n' +
        '        #allow-credentials: true\n' +
        '        #max-age: 1800\n' +
        '    mail:\n' +
        '        from: product@localhost\n' +
        '    swagger:\n' +
        '        default-include-pattern: /api/.*\n' +
        '        title: product API\n' +
        '        description: product API documentation\n' +
        '        version: 0.0.1\n' +
        '        terms-of-service-url:\n' +
        '        contact-name:\n' +
        '        contact-url:\n' +
        '        contact-email:\n' +
        '        license:\n' +
        '        license-url:\n' +
        '\n' +
        '# ===================================================================\n' +
        '# Application specific properties\n' +
        '# Add your own application properties here, see the ApplicationProperties class\n' +
        '# to have type-safe configuration, like in the JHipsterProperties above\n' +
        '#\n' +
        '# More documentation is available at:\n' +
        '# https://www.jhipster.tech/common-application-properties/\n' +
        '# ===================================================================\n' +
        '\n' +
        '# application:\n'
    }
  },
  mounted() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 210 + 'px'
    }
  }
}
</script>

<style scoped>

</style>
