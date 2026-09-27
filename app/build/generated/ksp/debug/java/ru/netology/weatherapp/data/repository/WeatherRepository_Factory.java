package ru.netology.weatherapp.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.netology.weatherapp.data.api.WeatherApiService;
import ru.netology.weatherapp.data.local.WeatherDao;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class WeatherRepository_Factory implements Factory<WeatherRepository> {
  private final Provider<WeatherApiService> apiServiceProvider;

  private final Provider<WeatherDao> daoProvider;

  public WeatherRepository_Factory(Provider<WeatherApiService> apiServiceProvider,
      Provider<WeatherDao> daoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public WeatherRepository get() {
    return newInstance(apiServiceProvider.get(), daoProvider.get());
  }

  public static WeatherRepository_Factory create(Provider<WeatherApiService> apiServiceProvider,
      Provider<WeatherDao> daoProvider) {
    return new WeatherRepository_Factory(apiServiceProvider, daoProvider);
  }

  public static WeatherRepository newInstance(WeatherApiService apiService, WeatherDao dao) {
    return new WeatherRepository(apiService, dao);
  }
}
