package me.zhengjie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

/**
 * @author Zheng Jie
 * @date 2025-06-25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "amz.s3")
public class AmzS3Config {

	/**
	 * Amazon S3 的区域配置，例如 "us-west-2"。
	 * 该区域决定了 S3 存储桶的地理位置。
	 */
	private String region;

	/**
	 * Amazon S3 的端点 URL
	 * 该端点用于访问 S3 服务。
	 */
	private String endPoint;

	/**
	 * Amazon S3 的域名
	 * 该域名用于构建访问 S3 服务的完整 URL。
	 */
	private String domain;

	/**
	 * Amazon S3 的访问密钥 ID，用于身份验证。
	 * 该密钥与 secretKey 一起使用来授权对 S3 服务的访问。
	 */
	private String accessKey;

	/**
	 * Amazon S3 的秘密访问密钥，用于身份验证。
	 * 该密钥与 accessKey 一起使用来授权对 S3 服务的访问。
	 */
	private String secretKey;

	/**
	 * 默认的 S3 存储桶名称。
	 * 该存储桶用于存储上传的文件和数据。
	 */
	private String defaultBucket;

	/**
	 * 文件上传后存储的文件夹格式，默认为 "yyyy-MM"。
	 */
	private String timeformat;

	/**
	 * 创建并返回一个 AmazonS3 客户端实例。
	 * 使用当前配置类的 endPoint, region, accessKey 和 secretKey。
	 * 声明为 @Bean 后，Spring 会将其作为单例管理，并在需要时自动注入。
	 *
	 * @return 配置好的 AmazonS3 客户端实例
	 */
	@Bean
	public S3Client amazonS3Client() {
		return S3Client.builder().region(Region.of(region))
				.endpointOverride(URI.create(endPoint))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
				.build();
	}
}