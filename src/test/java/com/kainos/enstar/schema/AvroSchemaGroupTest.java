package com.kainos.enstar.schema;

import com.kainos.enstar.dto.ColumnDefinitionChild;
import com.kainos.enstar.dto.DefinitionParent;
import com.kainos.enstar.source.LocalFilesystemSchemaSourceComplex;
import com.kainos.enstar.common.Table;
import org.apache.avro.Schema;
import org.junit.Test;
import org.junit.Assert;

import static com.kainos.enstar.common.Utils.EMPTY_STRING;

import static com.kainos.enstar.common.Utils.buildPathToTestInputData;
import static com.kainos.enstar.common.Utils.sortListSafely;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class AvroSchemaGroupTest {

    @Test
    public void getValidCommentForSingleSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemaonecomment/");
        Map<String, String> actualSchemaComments = myAvroSchemaGroup.getTableNamesAndDescriptionsOnly();

        Map<String, String> expectedTableComment = new HashMap<>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getValidCommentsForMultipleSchemas() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("multiplevalidavroschemas/");
        Map<String, String> actualSchemaComments = myAvroSchemaGroup.getTableNamesAndDescriptionsOnly();

        Map<String, String> expectedTableComment = new HashMap<>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getEmptyStringCommentForSingleSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemablankcomment/");
        Map<String, String> actualSchemaComments = myAvroSchemaGroup.getTableNamesAndDescriptionsOnly();

        Map<String, String> expectedTableComment = new HashMap<>();
        expectedTableComment.put("policy", EMPTY_STRING);

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getNoCommentFieldForOneSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemanocomment/");
        Map<String, String> actualSchemaComments = myAvroSchemaGroup.getTableNamesAndDescriptionsOnly();

        Map<String, String> expectedTableComment = new HashMap<>();
        expectedTableComment.put("policy", "");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getNoCommentFieldForOneOfMultipleSchemas() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemanocommentoneoftwo/");
        Map<String, String> actualSchemaComments = myAvroSchemaGroup.getTableNamesAndDescriptionsOnly();

        Map<String, String> expectedTableComment = new HashMap<>();
        expectedTableComment.put("policy", "");
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getOnePrimaryKeyFieldForOneSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemaoneprimarykey/");

        List<DefinitionParent> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns();

        Schema.Field mockSchemafield = mock(Schema.Field.class);
        when(mockSchemafield.name()).thenReturn("statsccyroe");

        ColumnDefinitionChild columnDefinition = new ColumnDefinitionChild(mockSchemafield,true);

        String actualColumnName = actualTableList.get(0).getChildDefinitionList().get(0).getName();
        String expectedColumnName = columnDefinition.getName();

        Assert.assertEquals(actualColumnName, expectedColumnName);
    }

    /*
    @Test
    public void getNoPrimaryKeyFieldForOneSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemanoprimarykey/");

        List<TableDefinitionParent> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns();

        List<String> actualColumnList = actualTableList.get(0).getColumns();
        Table expectedTable = new Table("policy", EMPTY_STRING, Collections.EMPTY_LIST);

        Assert.assertEquals("Mismatched table name, expected :policy", expectedTable.getName(), actualTableList.get(0).getName());
        Assert.assertTrue("Failed to return an empty list of columns.", actualColumnList.isEmpty());

    }

    @Test
    public void getMultiplePrimaryKeyFieldsForOneSchema() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("singleavroschemamultipleprimarykeys/");

        List<TableDefinitionParent> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns();

        List<String> expectedColumnList = Arrays.asList("decref", "programref", "statsccyroe");
        sortListSafely(expectedColumnList);

        Table expectedTable = new Table("policy", "This is the comment for policy", expectedColumnList);

        List<Table> expectedTableList = new ArrayList<>();
        expectedTableList.add(expectedTable);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

    }

    @Test
    public void getMultiplePrimaryKeyFieldsForMultipleSchemas() throws Exception {
        AvroSchemaGroupComplex myAvroSchemaGroup = getAvroSchemaGroup("multipleavroschemasmultipleprimarykeys/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");
        List<Table> expectedTableList = new ArrayList<>();

        List<String> expectedColumnList1 = Arrays.asList("claimcoveragecode", "exposuretypecode", "incidenttypecode", "record_validfrom", "record_operation", "claimkey", "exposurekey", "sourcesystemcode", "accidentyear");
        sortListSafely(expectedColumnList1);
        Table expectedTable1 = new Table("claim", EMPTY_STRING, expectedColumnList1);
        expectedTableList.add(expectedTable1);

        List<String> expectedColumnList2 = Arrays.asList("programref", "statsccyroe", "decref");
        sortListSafely(expectedColumnList2);
        Table expectedTable2 = new Table("policy", "This is the comment for policy", expectedColumnList2);
        expectedTableList.add(expectedTable2);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

    }*/

    private void assertTwoTableListsAreEqual(List<Table> expectedTableList, List<Table> actualTableList) {

        final int expectedIndex = 0;
        final int actualIndex = 1;

        sortListSafely(expectedTableList);
        sortListSafely(actualTableList);

        Assert.assertThat("Mismatch in sizes of expected and actual table lists.", expectedTableList.size(), is(actualTableList.size()));

        ParallelList<Table> expectedAndActualTableListOfLists = new ParallelList<>(expectedTableList, actualTableList);
        for (List<Table> tablePairList : expectedAndActualTableListOfLists) {

            Assert.assertThat("Mismatch in expected and actual table name.", tablePairList.get(expectedIndex).getName(), is(tablePairList.get(actualIndex).getName()));

            sortListSafely(tablePairList.get(expectedIndex).getColumns());
            sortListSafely(tablePairList.get(actualIndex).getColumns());

            Assert.assertThat("Mismatch in sizes of expected and actual column lists.", tablePairList.get(expectedIndex).getColumns().size(), is(tablePairList.get(actualIndex).getColumns().size()));
            Assert.assertThat("ColumnDefinitionChild lists have different values.", tablePairList.get(expectedIndex).getColumns(), is(tablePairList.get(actualIndex).getColumns()));
        }
    }

    private AvroSchemaGroupComplex getAvroSchemaGroup(String sourceSchemaDirectory) throws IOException {
        LocalFilesystemSchemaSourceComplex mockLocalFilesystemSchemaSource = getMockLocalFilesystemSchemaSource(sourceSchemaDirectory);
        return new AvroSchemaGroupComplex(mockLocalFilesystemSchemaSource);
    }

    private LocalFilesystemSchemaSourceComplex getMockLocalFilesystemSchemaSource(String relativePathForSchemas) throws IOException {
        File directory = new File(buildPathToTestInputData(relativePathForSchemas));

        LocalFilesystemSchemaSourceComplex mockLocalFilesystemSchemaSource = mock(LocalFilesystemSchemaSourceComplex.class);

        when(mockLocalFilesystemSchemaSource.getName()).thenReturn("Test Source Name");
        when(mockLocalFilesystemSchemaSource.getDescription()).thenReturn("Test Source Description");
        when(mockLocalFilesystemSchemaSource.getSchemaFieldPrimaryKeyIdentifier()).thenReturn("PK - ");

        Schema.Parser schemaParser = new Schema.Parser();
        List<Schema> schemas = new ArrayList<>();

        for (File schemaFile : directory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            schemas.add(schema);
        }

        when(mockLocalFilesystemSchemaSource.getSchemas()).thenReturn(schemas);

        return mockLocalFilesystemSchemaSource;
    }

    private class ParallelList<T> implements Iterable<List<T>> {

        private final List<List<T>> lists;

        public ParallelList(List<T> firstList, List<T> secondList) {
            this.lists = new ArrayList<>(2);
            this.lists.addAll(Arrays.asList(firstList, secondList));
        }

        public Iterator<List<T>> iterator() {
            return new Iterator<List<T>>() {
                private int loc = 0;

                public boolean hasNext() {
                    boolean hasNext = false;
                    for (List<T> list : lists) {
                        hasNext |= (loc < list.size());
                    }
                    return hasNext;
                }

                public List<T> next() {
                    List<T> vals = new ArrayList<>(lists.size());
                    for (List<T> list : lists) {
                        vals.add(loc < list.size() ? list.get(loc) : null);
                    }
                    loc++;
                    return vals;
                }

                public void remove() {
                    for (List<T> list : lists) {
                        if (loc < list.size()) {
                            list.remove(loc);
                        }
                    }
                }
            };
        }
    }
}