package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Muster16.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Muster16.class.getName() + ".mArtikels");
            createCache(cm, com.mycompany.myapp.domain.Muster16Ver.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Muster16Abg.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Muster16Abr.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MArtikel.class.getName());
            createCache(cm, com.mycompany.myapp.domain.M16Status.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PRezept.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PRezept.class.getName() + ".pCharges");
            createCache(cm, com.mycompany.myapp.domain.PRezept.class.getName() + ".pStatusInfos");
            createCache(cm, com.mycompany.myapp.domain.PRezeptVer.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PRezeptAbg.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PRezeptAbr.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PCharge.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PCharge.class.getName() + ".pWirkstoffs");
            createCache(cm, com.mycompany.myapp.domain.PWirkstoff.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PStatusInfo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PStatus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Apotheke.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Apotheke.class.getName() + ".lieferungs");
            createCache(cm, com.mycompany.myapp.domain.Apotheke.class.getName() + ".rechenzentrums");
            createCache(cm, com.mycompany.myapp.domain.EAenderung.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ERezept.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ERezept.class.getName() + ".eAenderungs");
            createCache(cm, com.mycompany.myapp.domain.EStatus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Lieferung.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Rechenzentrum.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Rechenzentrum.class.getName() + ".apothekes");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
