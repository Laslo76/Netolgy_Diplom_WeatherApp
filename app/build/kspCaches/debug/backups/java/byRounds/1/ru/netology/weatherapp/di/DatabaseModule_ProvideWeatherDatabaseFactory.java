package ru.netology.weatherapp.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.netology.weatherapp.data.local.WeatherDatabase;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideWeatherDatabaseFactory implements Factory<WeatherDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideWeatherDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WeatherDatabase get() {
    return provideWeatherDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideWeatherDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideWeatherDatabaseFactory(contextProvider);
  }

  public static WeatherDatabase provideWeatherDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWeatherDatabase(context));
  }
}
