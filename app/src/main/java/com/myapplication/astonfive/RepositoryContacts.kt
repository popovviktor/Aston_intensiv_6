package com.myapplication.astonfive

object RepositoryContacts {
    private val contactslist = ArrayList<Contact>()
    private var search_enabled:Boolean = false
    init {
        for (i in 0..200){
            val createContact = Contact(id = i,
            name = "Ivan"+i, surname = "Ivanov"+i,
            phone_number = "8777777"+i,
            photo_url = "https://loremflickr.com/300/300/person?random=${i+1}")
            contactslist.add(createContact)
        }
    }
    fun getList() = contactslist

    fun getContact(userId:Int):Contact?{
        for (elem in contactslist){
            if (userId==elem.id){
                return elem
            }
        }
    return null}
    fun delContact(contact: Contact){
        contactslist.remove(contact)
    }

    fun updateContact(userEdit:Contact){
        for (elem in contactslist){
            if (userEdit.id == elem.id){
                contactslist.set(userEdit.id,userEdit)
            }
        }
    }
    fun searchNameOrSurname(searchWord:String):ArrayList<Contact>{
        val findContacts = ArrayList<Contact>()
        for (elem in contactslist){
            if (elem.surname.indexOf(searchWord)!=-1){
                findContacts.add(elem)
            }else if (elem.name.indexOf(searchWord)!=-1){
                findContacts.add(elem)
            }
        }
        return findContacts
    }
    fun get_search_enabled():Boolean{
        return search_enabled
    }
    fun search_enable(){
        search_enabled = true
    }
    fun search_disable(){
        search_enabled = false
    }
}