package org.openstreetmap.josm.plugins.mapillary.testutils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;

import org.openstreetmap.josm.plugins.mapillary.cache.Caches;
import org.openstreetmap.josm.plugins.mapillary.model.ImageDetection;

import org.apache.commons.jcs3.access.CacheAccess;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@ExtendWith(MapillaryCaches.MapillaryCachesExtension.class)
public @interface MapillaryCaches {
  static class MapillaryCachesExtension implements AfterEachCallback, BeforeEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
      for (Field field : Caches.class.getDeclaredFields()) {
        if (field.getType().equals(Caches.MapillaryCacheAccess.class)) {
          final Caches.MapillaryCacheAccess mapillaryCacheAccess = ((Caches.MapillaryCacheAccess) field.get(null));
          mapillaryCacheAccess.getICacheAccess().clear();
          final Field rateLimited = Caches.MapillaryCacheAccess.class.getDeclaredField("rateLimited");
          rateLimited.setAccessible(true);
          rateLimited.setBoolean(mapillaryCacheAccess, false);
        }
      }
      // Clear image detection cache
      final Field imageDetectionCache = ImageDetection.class.getDeclaredField("DETECTION_CACHE");
      imageDetectionCache.setAccessible(true);
      ((CacheAccess<String, List<ImageDetection<?>>>) imageDetectionCache.get(null)).clear();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
      afterEach(context);
    }
  }
}
