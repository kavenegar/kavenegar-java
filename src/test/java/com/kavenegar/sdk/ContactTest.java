package com.kavenegar.sdk;

import com.kavenegar.sdk.models.ContactResult;
import com.kavenegar.sdk.models.GroupResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.random.RandomGenerator;

public class ContactTest {

    private String apiKey;
    private String receptor;
    private KavenegarClient api;

    @BeforeEach
    public void setUp() {
        apiKey = System.getenv().getOrDefault("KAVENEGAR_API_KEY", "<put your apikey here>");
        receptor = System.getenv().getOrDefault("KAVENEGAR_RECEPTOR", "<put your receptor here>");
        api = new KavenegarClient(apiKey);
    }

    @Test
    public void test_ListGroups() {
        List<GroupResult> res = api.listGroups();
        assertNotNull(res);
    }

    @Test
    public void test_ListGroups_Async() {
        List<GroupResult> res = api.listGroupsAsync().join();
        assertNotNull(res);
    }

    @Test
    public void test_AddGroup() {
        List<GroupResult> newGroups = api.addGroup("AddGroupTest1", "SaaS");
        assertNotNull(newGroups);
        if (!newGroups.isEmpty()) {
            api.removeGroup(newGroups.get(0).getGroupId());
        }
    }

    @Test
    public void test_AddGroup_Async() {
        List<GroupResult> newGroups = api.addGroupAsync("AddGroupTest1", "SaaS").join();
        assertNotNull(newGroups);
        if (!newGroups.isEmpty()) {
            api.removeGroupAsync(newGroups.get(0).getGroupId()).join();
        }
    }

    @Test
    public void test_EditGroup() {
        List<GroupResult> added = api.addGroup("EditGroupTest", "SaaS-Edit");
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            List<GroupResult> edited = api.editGroup(groupId, "EditGroupTestUpdated", null , "SaaS-Edit-Updated");
            assertNotNull(edited);
        }
    }

    @Test
    public void test_EditGroup_Async() {
        List<GroupResult> added = api.addGroupAsync("EditGroupTest", "SaaS-Edit").join();
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            List<GroupResult> edited = api.editGroupAsync(groupId, "EditGroupTestUpdated", null, "SaaS-Edit-Updated").join();
            assertNotNull(edited);
        }
    }

    @Test
    public void test_SearchGroups() {
        List<GroupResult> added = api.addGroup("SearchGroupTest", "SaaS-Search");
        assertNotNull(added);
        List<GroupResult> search = api.searchGroups("SaaS-Search");
        assertNotNull(search);
        if (!added.isEmpty()) {
            api.removeGroup(added.get(0).getGroupId());
        }
    }

    @Test
    public void test_SearchGroups_Async() {
        List<GroupResult> added = api.addGroupAsync("SearchGroupTest", "SaaS-Search").join();
        assertNotNull(added);
        List<GroupResult> search = api.searchGroupsAsync("SaaS-Search").join();
        assertNotNull(search);
        if (!added.isEmpty()) {
            api.removeGroupAsync(added.get(0).getGroupId()).join();
        }
    }

    @Test
    public void test_RemoveGroup() {
        List<GroupResult> added = api.addGroup("RemoveGroupTest", "SaaS-Remove");
        assertNotNull(added);
        if (!added.isEmpty()) {
            List<GroupResult> remainingGroups = api.removeGroup(added.get(0).getGroupId());
            assertNotNull(remainingGroups);
        }
    }

    @Test
    public void test_RemoveGroup_Async() {
        List<GroupResult> added = api.addGroupAsync("RemoveGroupTest", "SaaS-Remove").join();
        assertNotNull(added);
        if (!added.isEmpty()) {
            List<GroupResult> remainingGroups = api.removeGroupAsync(added.get(0).getGroupId()).join();
            assertNotNull(remainingGroups);
        }
    }

    @Test
    public void test_AddContact() {
        RandomGenerator random = RandomGenerator.getDefault();
        List<GroupResult> added = api.addGroup(String.format("AddContactTest %d",random.nextInt(1,100000)), "SaaS-Contact");
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            List<ContactResult> contacts = api.addContact(groupId, receptor, "تست افزودن مخاطب", null, null, null);
            assertNotNull(contacts);
            api.removeGroup(groupId);
        }
    }

    @Test
    public void test_AddContact_Async() {
        RandomGenerator random = RandomGenerator.getDefault();
        List<GroupResult> added = api.addGroupAsync(String.format("AddContactTest %d",random.nextInt(1,100000)), "SaaS-Contact").join();
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            List<ContactResult> contacts = api.addContactAsync(groupId, receptor, "تست افزودن مخاطب", null, null, null).join();
            assertNotNull(contacts);
            api.removeGroupAsync(groupId).join();
        }
    }

    @Test
    public void test_RemoveContact() {
        List<GroupResult> added = api.addGroup("RemoveContactTest", "SaaS-RemoveContact");
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            api.addContact(groupId, receptor, "تست حذف مخاطب", null, null, null);
            List<ContactResult> remainingContacts = api.removeContact(null, receptor, groupId);
            assertNotNull(remainingContacts);
            api.removeGroup(groupId);
        }
    }

    @Test
    public void test_RemoveContact_Async() {
        List<GroupResult> added = api.addGroupAsync("RemoveContactTest", "SaaS-RemoveContact").join();
        assertNotNull(added);
        if (!added.isEmpty()) {
            int groupId = added.get(0).getGroupId();
            api.addContactAsync(groupId, receptor, "تست حذف مخاطب", null, null, null).join();
            List<ContactResult> remainingContacts = api.removeContactAsync(null, receptor, groupId).join();
            assertNotNull(remainingContacts);
            api.removeGroupAsync(groupId).join();
        }
    }
}
