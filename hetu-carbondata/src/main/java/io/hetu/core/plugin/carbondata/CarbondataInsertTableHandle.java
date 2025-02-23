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

package io.hetu.core.plugin.carbondata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.prestosql.plugin.hive.HiveBucketProperty;
import io.prestosql.plugin.hive.HiveColumnHandle;
import io.prestosql.plugin.hive.HiveInsertTableHandle;
import io.prestosql.plugin.hive.HiveStorageFormat;
import io.prestosql.plugin.hive.LocationHandle;
import io.prestosql.plugin.hive.metastore.HivePageSinkMetadata;
import io.prestosql.spi.connector.ConnectorInsertTableHandle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class CarbondataInsertTableHandle
        extends HiveInsertTableHandle
        implements ConnectorInsertTableHandle
{
    private final Map<String, String> additionalConf;

    @JsonCreator
    public CarbondataInsertTableHandle(@JsonProperty("schemaName") String schemaName,
                                       @JsonProperty("tableName") String tableName,
                                       @JsonProperty("inputColumns") List<HiveColumnHandle> inputColumns,
                                       @JsonProperty("pageSinkMetadata") HivePageSinkMetadata pageSinkMetadata,
                                       @JsonProperty("locationHandle") LocationHandle locationHandle,
                                       @JsonProperty("bucketProperty") Optional<HiveBucketProperty> bucketProperty,
                                       @JsonProperty("tableStorageFormat") HiveStorageFormat tableStorageFormat,
                                       @JsonProperty("partitionStorageFormat") HiveStorageFormat partitionStorageFormat,
                                       @JsonProperty("additionalConf") Map<String, String> additionalConf,
                                       @JsonProperty("isOverwrite") boolean isOverwrite)
    {
        super(schemaName, tableName, inputColumns, pageSinkMetadata,
                locationHandle, bucketProperty, tableStorageFormat,
                partitionStorageFormat, isOverwrite, false);

        this.additionalConf = ImmutableMap.copyOf(requireNonNull(additionalConf, "additional conf map is null"));
    }

    @JsonProperty
    public Map<String, String> getAdditionalConf()
    {
        return additionalConf;
    }
}
