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
package io.hetu.core.plugin.mariadb;

import io.airlift.configuration.Config;
import io.airlift.units.Duration;

import javax.validation.constraints.Min;

import java.util.concurrent.TimeUnit;

public class MariaConfig
{
    private boolean autoReconnect = true;
    private int maxReconnects = 3;
    private Duration connectionTimeout = new Duration(10, TimeUnit.SECONDS);

    public boolean isAutoReconnect()
    {
        return autoReconnect;
    }

    @Config("maria.auto-reconnect")
    public MariaConfig setAutoReconnect(boolean autoReconnect)
    {
        this.autoReconnect = autoReconnect;
        return this;
    }

    @Min(1)
    public int getMaxReconnects()
    {
        return maxReconnects;
    }

    @Config("maria.max-reconnects")
    public MariaConfig setMaxReconnects(int maxReconnects)
    {
        this.maxReconnects = maxReconnects;
        return this;
    }

    public Duration getConnectionTimeout()
    {
        return connectionTimeout;
    }

    @Config("maria.connection-timeout")
    public MariaConfig setConnectionTimeout(Duration connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
        return this;
    }
}
