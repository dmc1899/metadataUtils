package com.kainos.enstar.schema;

import com.kainos.enstar.source.LocalFilesystemSchemaSource;
import com.kainos.enstar.common.Table;
import org.apache.avro.Schema;
import org.junit.Test;
import org.junit.Assert;

import static com.kainos.enstar.common.Utils.sortListSafely;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

/**
 *
 */
public class AvroSchemaGroupTest {
    @Test
    public void getValidCommentForSingleSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanocomment/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getValidCommentsForMultipleSchemas() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/multiplevalidavroschemas/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getEmptyStringCommentForSingleSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemablankcomment/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getNoCommentFieldForOneSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanocomment/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", null);

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getNoCommentFieldForOneOfMultipleSchemas() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanocommentoneoftwo/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", null);
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getOnePrimaryKeyFieldForOneSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemaoneprimarykey/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");

        List<String> expectedColumnList = Arrays.asList("statsccyroe");
        Collections.sort(expectedColumnList);

        Table expectedTable = new Table("policy", null, expectedColumnList);

        List<Table> expectedTableList = new ArrayList<Table>();
        expectedTableList.add(expectedTable);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

    }

    @Test
    public void getNoPrimaryKeyFieldForOneSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanoprimarykey/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PKINVALID - ");

        List<String> actualColumnList = actualTableList.get(0).getColumns();
        Table expectedTable = new Table("policy", null, null);

        Assert.assertEquals("Mismatched table name, expected :policy", expectedTable.getTableName(), actualTableList.get(0).getTableName());
        Assert.assertTrue("Failed to return an empty list of columns.", actualColumnList.isEmpty());

    }

    @Test
    public void getMultiplePrimaryKeyFieldsForOneSchema() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemamultipleprimarykeys/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");

        List<String> expectedColumnList = Arrays.asList("decref", "programref", "statsccyroe");
        sortListSafely(expectedColumnList);

        Table expectedTable = new Table("policy", "This is the comment for policy", expectedColumnList);

        List<Table> expectedTableList = new ArrayList<Table>();
        expectedTableList.add(expectedTable);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

    }

    @Test
    public void getMultiplePrimaryKeyFieldsForMultipleSchemas() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/multipleavroschemasmultipleprimarykeys/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");
        List<Table> expectedTableList = new ArrayList<Table>();

        List<String> expectedColumnList1 = Arrays.asList("claimcoveragecode", "exposuretypecode", "incidenttypecode", "record_validfrom", "record_operation", "claimkey", "exposurekey", "sourcesystemcode", "accidentyear");
        sortListSafely(expectedColumnList1);
        Table expectedTable1 = new Table("claim", null, expectedColumnList1);
        expectedTableList.add(expectedTable1);

        List<String> expectedColumnList2 = Arrays.asList("programref", "statsccyroe", "decref");
        sortListSafely(expectedColumnList2);
        Table expectedTable2 = new Table("policy", "This is the comment for policy", expectedColumnList2);
        expectedTableList.add(expectedTable2);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

    }

    private void assertTwoTableListsAreEqual(List<Table> expectedTableList, List<Table> actualTableList) {

        final int EXPECTED_INDEX = 0;
        final int ACTUAL_INDEX = 1;

        sortListSafely(expectedTableList);
        sortListSafely(actualTableList);

        Assert.assertThat("Mismatch in sizes of expected and actual table lists.", expectedTableList.size(), is(actualTableList.size()));

        ParallelList<Table> expectedAndActualTableListOfLists = new ParallelList<Table>(expectedTableList, actualTableList);
        for (List<Table> tablePairList : expectedAndActualTableListOfLists) {

            Assert.assertThat("Mismatch in expected and actual table name.", tablePairList.get(EXPECTED_INDEX).getTableName(), is(tablePairList.get(ACTUAL_INDEX).getTableName()));

            sortListSafely(tablePairList.get(EXPECTED_INDEX).getColumns());
            sortListSafely(tablePairList.get(ACTUAL_INDEX).getColumns());

            Assert.assertThat("Mismatch in sizes of expected and actual column lists.", tablePairList.get(EXPECTED_INDEX).getColumns().size(), is(tablePairList.get(ACTUAL_INDEX).getColumns().size()));
            Assert.assertThat("Column lists have different values.", tablePairList.get(EXPECTED_INDEX).getColumns(), is(tablePairList.get(ACTUAL_INDEX).getColumns()));
        }
    }

    private AvroSchemaGroup getAvroSchemaGroup(String sourceSchemaDirectory) throws IOException {
        LocalFilesystemSchemaSource mockLocalFilesystemSchemaSource = getMockLocalFilesystemSchemaSource(sourceSchemaDirectory);
        return new AvroSchemaGroup(mockLocalFilesystemSchemaSource);
    }

    private LocalFilesystemSchemaSource getMockLocalFilesystemSchemaSource(String relativePathForSchemas) throws IOException {
        String testInputDataDirectory = this.getClass().getClassLoader().getResource("").getPath() + relativePathForSchemas;

        Schema.Parser schemaParser = new Schema.Parser();
        ArrayList<Schema> schemas = new ArrayList<Schema>();

        File directory = new File(testInputDataDirectory);
        for (File schemaFile : directory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            schemas.add(schema);
        }

        LocalFilesystemSchemaSource mockLocalFilesystemSchemaSource = mock(LocalFilesystemSchemaSource.class);
        when(mockLocalFilesystemSchemaSource.getSchemas()).thenReturn(schemas);

        return mockLocalFilesystemSchemaSource;
    }

    private class ParallelList<T> implements Iterable<List<T>> {

        private final List<List<T>> lists;

        public ParallelList(List<T> firstList, List<T> secondList) {
            this.lists = new ArrayList<List<T>>(2);
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
                    List<T> vals = new ArrayList<T>(lists.size());
                    for (int i = 0; i < lists.size(); i++) {
                        vals.add(loc < lists.get(i).size() ? lists.get(i).get(loc) : null);
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