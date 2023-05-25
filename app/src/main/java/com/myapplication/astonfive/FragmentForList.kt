package com.myapplication.astonfive

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentForList : Fragment(),
    AdapterForContactList.onClickListener,
    AdapterForContactList.onLongClickListener{
    private val adapterForContactList = AdapterForContactList()
    lateinit var recycler_view_contacts: RecyclerView
    lateinit var searchEdit:EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchAndRecyclerView(view)
        initSearchAddTextWatcher()
    }
    fun initSearchAddTextWatcher(){
        searchEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (s?.length!!>0) {
                    val find_contacts = RepositoryContacts.searchNameOrSurname(s.toString())
                    clearListForCorrectDrawContactsWhenChangedSearch()
                    adapterForContactList.contact_list = find_contacts
                    RepositoryContacts.search_enable()
                }else{
                    clearListForCorrectDrawContactsWhenChangedSearch()
                    adapterForContactList.contact_list = RepositoryContacts.getList()
                    RepositoryContacts.search_disable()
                }
            }
        })
    }
    fun clearListForCorrectDrawContactsWhenChangedSearch(){
        adapterForContactList.contact_list = ArrayList<Contact>()
    }
    fun initSearchAndRecyclerView(view: View){
        searchEdit = view.findViewById(R.id.edit_search)
        recycler_view_contacts = view.findViewById(R.id.recycler_view_for_list)
        recycler_view_contacts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterForContactList.onclickListener = this
        adapterForContactList.onlongClickListener = this
        initAddItemDecotations()
        recycler_view_contacts.adapter = adapterForContactList
        adapterForContactList.contact_list = RepositoryContacts.getList()
    }
    fun initAddItemDecotations(){
        val dividerItemDecoration = DividerItemDecoration(activity,RecyclerView.VERTICAL)
        val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.divided_drawble)
        if (drawable!=null){
            dividerItemDecoration.setDrawable(drawable)
            recycler_view_contacts.addItemDecoration(dividerItemDecoration)
        }
        recycler_view_contacts.addItemDecoration(VerticalSpaceItemDecoration(5))

    }
    fun startFragmentEditContactWithBundleIdItem(id_contact:Int){
        val fragForEdit = FragmentForEditItem()
        val bundle = Bundle()
        bundle.putInt(Constants.ID_KEY,id_contact)
        fragForEdit.arguments = bundle
        val smallestScreenDp = requireActivity().resources.configuration.smallestScreenWidthDp
        if (smallestScreenDp>Constants.MIN_DP_FOR_TABLE){
            startForTableMode(fragForEdit)
        }else{
            settingForPortaitAndLansdscapeForPhone(fragForEdit)
        }
    }
    fun settingForPortaitAndLansdscapeForPhone(fragmentEdit:Fragment){
        val orientation = requireActivity().resources.configuration.orientation
        if (orientation==Constants.LANDSCAPE_ORIENTATION){
            startForLandscapeMode(fragmentEdit)
        }else{
            startPortraitMode(fragmentEdit)
        }
    }
    fun startPortraitMode(fragmentEdit:Fragment){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragmentEdit)
            .addToBackStack(null)
            .commit()
    }
    fun startForLandscapeMode(fragmentEdit:Fragment){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container2,fragmentEdit)
            .addToBackStack(null)
            .commit()
    }
    fun startForTableMode(fragmentEdit:Fragment){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container3,fragmentEdit)
            .addToBackStack(null)
            .commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_for_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentForList()
    }

    override fun clickListener(contact: Contact) {
        val id = contact.id
        startFragmentEditContactWithBundleIdItem(id)
    }

    override fun longClickListener(contact: Contact) {
        RepositoryContacts.delContact(contact)
        if (RepositoryContacts.get_search_enabled()==false){
            adapterForContactList.contact_list = RepositoryContacts.getList()
        }else{
            val search_word = searchEdit.text
            val findContactsAfterDelItem = RepositoryContacts.searchNameOrSurname(search_word.toString())
            adapterForContactList.contact_list = findContactsAfterDelItem
        }
        recycler_view_contacts.adapter?.notifyDataSetChanged()
    }
}