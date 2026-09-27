package ru.netology.weatherapp;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.netology.weatherapp.data.prefs.SettingsManager;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<SettingsManager> settingsManagerProvider;

  public MainActivity_MembersInjector(Provider<SettingsManager> settingsManagerProvider) {
    this.settingsManagerProvider = settingsManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<SettingsManager> settingsManagerProvider) {
    return new MainActivity_MembersInjector(settingsManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectSettingsManager(instance, settingsManagerProvider.get());
  }

  @InjectedFieldSignature("ru.netology.weatherapp.MainActivity.settingsManager")
  public static void injectSettingsManager(MainActivity instance, SettingsManager settingsManager) {
    instance.settingsManager = settingsManager;
  }
}
