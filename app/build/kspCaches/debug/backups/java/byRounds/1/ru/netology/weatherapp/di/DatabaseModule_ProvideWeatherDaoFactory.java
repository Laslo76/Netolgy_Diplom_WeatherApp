package ru.netology.weatherapp.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.netology.weatherapp.data.local.WeatherDao;
import ru.netology.weatherapp.data.local.WeatherDatabase;

@ScopeMetadata
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
public final class DatabaseModule_ProvideWeatherDaoFactory implements Factory<WeatherDao> {
  private final Provider<WeatherDatabase> databaseProvider;

  public DatabaseModule_ProvideWeatherDaoFactory(Provider<WeatherDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WeatherDao get() {
    return provideWeatherDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWeatherDaoFactory create(
      Provider<WeatherDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWeatherDaoFactory(databaseProvider);
  }

  public static WeatherDao provideWeatherDao(WeatherDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWeatherDao(database));
  }
}
