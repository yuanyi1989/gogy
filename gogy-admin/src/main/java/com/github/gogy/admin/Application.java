/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import javax.annotation.Resource;

/**
 * @author yuanyi
 * @date 2018/1/13
 */
@Configuration
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    @Resource
    ResourceProperties resourceProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        customizeResourceHandler(registry.addResourceHandler("/third/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(resourceProperties.getCachePeriod()));

        customizeResourceHandler(registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(resourceProperties.getCachePeriod()));
    }

    public void customizeResourceHandler(ResourceHandlerRegistration registration) {
        ResourceProperties.Chain properties = this.resourceProperties.getChain();
        configureResourceChain(properties,
                registration.resourceChain(properties.isCache()));
    }

    private void configureResourceChain(ResourceProperties.Chain properties,
                                        ResourceChainRegistration chain) {
        ResourceProperties.Strategy strategy = properties.getStrategy();
        if (strategy.getFixed().isEnabled() || strategy.getContent().isEnabled()) {
            chain.addResolver(getVersionResourceResolver(strategy));
        }
        if (properties.isGzipped()) {
            chain.addResolver(new GzipResourceResolver());
        }
        if (properties.isHtmlApplicationCache()) {
            chain.addTransformer(new AppCacheManifestTransformer());
        }
    }

    private ResourceResolver getVersionResourceResolver(
            ResourceProperties.Strategy properties) {
        VersionResourceResolver resolver = new VersionResourceResolver();
        if (properties.getFixed().isEnabled()) {
            String version = properties.getFixed().getVersion();
            String[] paths = properties.getFixed().getPaths();
            resolver.addFixedVersionStrategy(version, paths);
        }
        if (properties.getContent().isEnabled()) {
            String[] paths = properties.getContent().getPaths();
            resolver.addContentVersionStrategy(paths);
        }
        return resolver;
    }
}
