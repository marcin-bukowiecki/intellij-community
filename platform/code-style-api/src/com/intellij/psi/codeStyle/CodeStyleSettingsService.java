// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.psi.codeStyle;

import com.intellij.lang.Language;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@ApiStatus.Internal
public interface CodeStyleSettingsService {
  static CodeStyleSettingsService getInstance() {
    return ApplicationManager.getApplication().getService(CodeStyleSettingsService.class);
  }

  @Nullable
  static LanguageCodeStyleProvider getLanguageCodeStyleProvider(Language language) {
    for (LanguageCodeStyleProvider provider : getInstance().getLanguageCodeStyleProviders()) {
      if (provider.getLanguage().equals(language)) {
        return provider;
      }
    }
    return null;
  }

  @Nullable
  static LanguageCodeStyleProvider getLanguageCodeStyleProviderUsingBaseLanguage(@NotNull Language language) {
    for (Language currLang = language; currLang != null;  currLang = currLang.getBaseLanguage()) {
      LanguageCodeStyleProvider curr = getLanguageCodeStyleProvider(currLang);
      if (curr != null) return curr;
    }
    return null;
  }

  void addListener(@NotNull CodeStyleSettingsServiceListener listener, @Nullable Disposable disposable);

  @NotNull
  List<? extends FileTypeIndentOptionsFactory> getFileTypeIndentOptionsFactories();

  @NotNull
  List<? extends CustomCodeStyleSettingsFactory> getCustomCodeStyleSettingsFactories();

  @NotNull
  List<? extends LanguageCodeStyleProvider> getLanguageCodeStyleProviders();

  /**
   * @deprecated Use {@link #getCustomCodeStyleSettingsFactories()} which returns all factories, including settings pages.
   */
  @Deprecated
  @ApiStatus.ScheduledForRemoval(inVersion = "2022.1")
  @NotNull
  Set<? extends CustomCodeStyleSettingsFactory> getSettingsPagesProviders();
}