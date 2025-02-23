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
package io.prestosql.elasticsearch.optimization;

public class ElasticSearchConverterContext
{
    private boolean hasConversionFailed;

    private int derefereneceLevel;

    public ElasticSearchConverterContext()
    {
        this.hasConversionFailed = false;
        this.derefereneceLevel = 0;
    }

    public boolean isHasConversionFailed()
    {
        return hasConversionFailed;
    }

    public void setConversionFailed()
    {
        this.hasConversionFailed = true;
    }

    public boolean isInDeference()
    {
        return derefereneceLevel > 0;
    }

    public void stepInDeference()
    {
        ++derefereneceLevel;
    }

    public void stepOutDeference()
    {
        if (derefereneceLevel > 0) {
            --derefereneceLevel;
        }
    }
}
