/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.spi.connector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.prestosql.spi.type.Type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.prestosql.spi.connector.SchemaUtil.checkNotEmpty;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;

public class ColumnMetadata
{
    private final String name;
    private final Type type;
    private final boolean nullable;
    private final String comment;
    private final String extraInfo;
    private final boolean hidden;
    private final boolean required;
    private final Map<String, Object> properties;

    public ColumnMetadata(String name, Type type)
    {
        this(name, type, true, null, null, false, emptyMap(), false);
    }

    public ColumnMetadata(String name, Type type, String comment, boolean hidden)
    {
        this(name, type, true, comment, null, hidden, emptyMap(), false);
    }

    public ColumnMetadata(String name, Type type, String comment, String extraInfo, boolean hidden)
    {
        this(name, type, true, comment, extraInfo, hidden, emptyMap(), false);
    }

    public ColumnMetadata(String name, Type type, String comment, String extraInfo, boolean hidden, Map<String, Object> properties)
    {
        this(name, type, true, comment, extraInfo, hidden, properties);
    }

    public ColumnMetadata(String name, Type type, boolean nullable, String comment, String extraInfo, boolean hidden, Map<String, Object> properties)
    {
        this(name, type, nullable, comment, extraInfo, hidden, properties, false);
    }

    @JsonCreator
    public ColumnMetadata(
            @JsonProperty("name") String name,
            @JsonProperty("type") Type type,
            @JsonProperty("nullable") boolean nullable,
            @JsonProperty("comment") String comment,
            @JsonProperty("extraInfo") String extraInfo,
            @JsonProperty("hidden") boolean hidden,
            @JsonProperty("properties") Map<String, Object> properties,
            @JsonProperty("required") boolean required)
    {
        checkNotEmpty(name, "name");
        requireNonNull(type, "type is null");
        requireNonNull(properties, "properties is null");

        this.name = name.toLowerCase(ENGLISH);
        this.type = type;
        this.comment = comment;
        this.extraInfo = extraInfo;
        this.hidden = hidden;
        this.properties = properties.isEmpty() ? emptyMap() : unmodifiableMap(new LinkedHashMap<>(properties));
        this.nullable = nullable;
        this.required = required;
    }

    @JsonProperty
    public String getName()
    {
        return name;
    }

    @JsonProperty
    public Type getType()
    {
        return type;
    }

    @JsonProperty
    public boolean isNullable()
    {
        return nullable;
    }

    @JsonProperty
    public String getComment()
    {
        return comment;
    }

    @JsonProperty
    public String getExtraInfo()
    {
        return extraInfo;
    }

    @JsonProperty
    public boolean isHidden()
    {
        return hidden;
    }

    @JsonProperty
    public boolean isRequired()
    {
        return required;
    }

    @JsonProperty
    public Map<String, Object> getProperties()
    {
        return properties;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ColumnMetadata{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", ").append(nullable ? "nullable" : "nonnull");
        if (comment != null) {
            sb.append(", comment='").append(comment).append('\'');
        }
        if (extraInfo != null) {
            sb.append(", extraInfo='").append(extraInfo).append('\'');
        }
        if (hidden) {
            sb.append(", hidden");
        }
        if (required) {
            sb.append(", required");
        }
        if (!properties.isEmpty()) {
            sb.append(", properties=").append(properties);
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, type, nullable, comment, extraInfo, hidden, required);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColumnMetadata other = (ColumnMetadata) obj;
        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.type, other.type) &&
                Objects.equals(this.nullable, other.nullable) &&
                Objects.equals(this.comment, other.comment) &&
                Objects.equals(this.extraInfo, other.extraInfo) &&
                Objects.equals(this.required, other.required) &&
                Objects.equals(this.hidden, other.hidden);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String name;
        private Type type;
        private boolean nullable = true;
        private Optional<String> comment = Optional.empty();
        private Optional<String> extraInfo = Optional.empty();
        private boolean hidden;
        private Map<String, Object> properties = emptyMap();

        private Builder() {}

        private Builder(ColumnMetadata columnMetadata)
        {
            this.name = columnMetadata.getName();
            this.type = columnMetadata.getType();
            this.nullable = columnMetadata.isNullable();
            this.comment = Optional.ofNullable(columnMetadata.getComment());
            this.extraInfo = Optional.ofNullable(columnMetadata.getExtraInfo());
            this.hidden = columnMetadata.isHidden();
            this.properties = columnMetadata.getProperties();
        }

        public Builder setName(String name)
        {
            this.name = requireNonNull(name, "name is null");
            return this;
        }

        public Builder setType(Type type)
        {
            this.type = requireNonNull(type, "type is null");
            return this;
        }

        public Builder setNullable(boolean nullable)
        {
            this.nullable = nullable;
            return this;
        }

        public Builder setComment(Optional<String> comment)
        {
            this.comment = requireNonNull(comment, "comment is null");
            return this;
        }

        public Builder setExtraInfo(Optional<String> extraInfo)
        {
            this.extraInfo = requireNonNull(extraInfo, "extraInfo is null");
            return this;
        }

        public Builder setHidden(boolean hidden)
        {
            this.hidden = hidden;
            return this;
        }

        public Builder setProperties(Map<String, Object> properties)
        {
            this.properties = requireNonNull(properties, "properties is null");
            return this;
        }

        public ColumnMetadata build()
        {
            return new ColumnMetadata(
                    name,
                    type,
                    nullable,
                    comment.orElse(null),
                    extraInfo.orElse(null),
                    hidden,
                    properties);
        }
    }

    public ColumnSchema getColumnSchema()
    {
        return ColumnSchema.builder(this)
                .build();
    }
}
