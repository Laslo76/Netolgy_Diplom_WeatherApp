package ru.netology.weatherapp.ui.forecast;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.netology.weatherapp.data.prefs.SettingsManager;
import ru.netology.weatherapp.data.repository.WeatherRepository;

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
public final class ForecastViewModel_Factory implements Factory<ForecastViewModel> {
  private final Provider<WeatherRepository> repositoryProvider;

  private final Provider<SettingsManager> settingsManagerProvider;

  public ForecastViewModel_Factory(Provider<WeatherRepository> repositoryProvider,
      Provider<SettingsManager> settingsManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.settingsManagerProvider = settingsManagerProvider;
  }

  @Override
  public ForecastViewModel get() {
    return newInstance(repositoryProvider.get(), settingsManagerProvider.get());
  }

  public static ForecastViewModel_Factory create(Provider<WeatherRepository> repositoryProvider,
      Provider<SettingsManager> settingsManagerProvider) {
    return new ForecastViewModel_Factory(repositoryProvider, settingsManagerProvider);
  }

  public static ForecastViewModel newInstance(WeatherRepository repository,
      SettingsManager settingsManager) {
    return new ForecastViewModel(repository, settingsManager);
  }
}
