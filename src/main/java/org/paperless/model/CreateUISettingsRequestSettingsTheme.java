package org.paperless.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * CreateUISettingsRequestSettingsTheme
 */

@JsonTypeName("CreateUISettings_request_settings_theme")
@Generated(value = "org.paperless.codegen.languages.SpringCodegen", date = "2024-02-17T12:03:05.936104740Z[Etc/UTC]")
public class CreateUISettingsRequestSettingsTheme {

  private String color;

  public CreateUISettingsRequestSettingsTheme() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateUISettingsRequestSettingsTheme(String color) {
    this.color = color;
  }

  public CreateUISettingsRequestSettingsTheme color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
  */
  @NotNull 
  @Schema(name = "color", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("color")
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateUISettingsRequestSettingsTheme createUISettingsRequestSettingsTheme = (CreateUISettingsRequestSettingsTheme) o;
    return Objects.equals(this.color, createUISettingsRequestSettingsTheme.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateUISettingsRequestSettingsTheme {\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

