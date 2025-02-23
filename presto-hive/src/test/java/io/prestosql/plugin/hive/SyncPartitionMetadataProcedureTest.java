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
package io.prestosql.plugin.hive;

import io.prestosql.spi.procedure.Procedure;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;

public class SyncPartitionMetadataProcedureTest
{
    @Mock
    private HdfsEnvironment mockHdfsEnvironment;

    private SyncPartitionMetadataProcedure syncPartitionMetadataProcedureUnderTest;

    @BeforeMethod
    public void setUp() throws Exception
    {
        initMocks(this);
        TransactionalMetadata transactionalMetadata = new TransactionalMetadata()
        {
            @Override
            public void commit()
            {
            }

            @Override
            public void rollback()
            {
            }
        };
        syncPartitionMetadataProcedureUnderTest = new SyncPartitionMetadataProcedure(() -> transactionalMetadata, mockHdfsEnvironment);
    }

    @Test
    public void testGet() throws Exception
    {
        // Setup
        // Run the test
        final Procedure result = syncPartitionMetadataProcedureUnderTest.get();

        // Verify the results
    }

    @Test
    public void testSyncPartitionMetadata()
    {
        // Setup
        syncPartitionMetadataProcedureUnderTest.syncPartitionMetadata(new VacuumCleanerTest.ConnectorSession(), "schemaName", "tableName", "add");
    }
}
