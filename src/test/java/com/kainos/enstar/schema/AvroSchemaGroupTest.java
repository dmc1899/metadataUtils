package com.kainos.enstar.schema;

import com.kainos.enstar.source.LocalFilesystemSchemaSource;
import com.kainos.enstar.source.SchemaSource;
import com.kainos.enstar.common.Table;
import com.kainos.enstar.schema.AvroSchemaGroup;
import org.apache.avro.Schema;
import org.junit.Test;
import org.junit.Assert;

import org.hamcrest.collection.IsMapContaining;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

/**
 *
 */
public class AvroSchemaGroupTest
{
    private static final int EXPECTED_INDEX = 0;
    private static final int ACTUAL_INDEX = 1;

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
        expectedTableComment.put("policy",  null);

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getNoCommentFieldForOneOfMultipleSchemas() throws Exception {
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanocommentoneoftwo/");

        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy",  null);
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));
    }

    @Test
    public void getOnePrimaryKeyFieldForOneSchema() throws Exception{
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
    public void getNoPrimaryKeyFieldForOneSchema() throws Exception{
        AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singleavroschemanoprimarykey/");

        List<Table> actualTableList = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PKINVALID - ");

        List<String> expectedColumnList = null;

        Table expectedTable = new Table("policy", null, expectedColumnList);

        List<Table> expectedTableList = new ArrayList<Table>();
        expectedTableList.add(expectedTable);

        assertTwoTableListsAreEqual(expectedTableList, actualTableList);

        //TODO - custom code to verify that the inner list is NULL but the outer list has policy.

    }


    // Utility method
@Test
    public void testSort() throws Exception{
    AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singlevalidavroschema/");
    List<Table> actualTableList = myAvroSchemaGroup.getTablesAndColumns();

    System.out.println(actualTableList.get(0).getColumns());
    sortList(actualTableList.get(0).getColumns());
    System.out.println(actualTableList.get(0).getColumns());

}

    private void assertTwoTableListsAreEqual(List<Table> expectedTableList, List<Table> actualTableList){

        //Collections.sort(expectedTableList);
        //Collections.sort(actualTableList);
        sortList(expectedTableList);
        sortList(actualTableList);

        Assert.assertThat("Mismatch in sizes of expected and actual table lists.", expectedTableList.size(), is(actualTableList.size()));

        ParallelList<Table> expectedAndActualTableList = new ParallelList<Table>(expectedTableList, actualTableList);
        for (List<Table> tablePair : expectedAndActualTableList) {

            Assert.assertThat("Mismatch in expected and actual table name.", tablePair.get(EXPECTED_INDEX).getTableName(), is(tablePair.get(ACTUAL_INDEX).getTableName()));

            sortList(tablePair.get(EXPECTED_INDEX).getColumns());
            sortList(tablePair.get(ACTUAL_INDEX).getColumns());

            Assert.assertThat("Mismatch in sizes of expected and actual column lists.", tablePair.get(EXPECTED_INDEX).getColumns().size(), is(tablePair.get(ACTUAL_INDEX).getColumns().size()));
            Assert.assertThat("Column lists have different values.", tablePair.get(EXPECTED_INDEX).getColumns(), is(tablePair.get(ACTUAL_INDEX).getColumns()));
        }
    }


    private <T extends Comparable<T>> void sortList(List<T> listToBeSorted) {
        if (listToBeSorted != null){
            Collections.sort(listToBeSorted);
        }
    }

    private AvroSchemaGroup getAvroSchemaGroup(String sourceSchemaDirectory) throws IOException {
        LocalFilesystemSchemaSource mockLocalFilesystemSchemaSource = getMockLocalFilesystemSchemaSource(sourceSchemaDirectory);
        return new AvroSchemaGroup(mockLocalFilesystemSchemaSource);
    }

    private LocalFilesystemSchemaSource getMockLocalFilesystemSchemaSource(String relativePathForSchemas) throws IOException {
        String testInputDataDirectory = this.getClass().getClassLoader().getResource("").getPath()+ relativePathForSchemas;

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

        public ParallelList(List<T>... lists) {
            this.lists = new ArrayList<List<T>>(lists.length);
            this.lists.addAll(Arrays.asList(lists));
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
                    for (int i=0; i<lists.size(); i++) {
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