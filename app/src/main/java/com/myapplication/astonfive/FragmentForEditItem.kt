package com.myapplication.astonfive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView


class FragmentForEditItem : Fragment() {
    lateinit var edit_name:EditText
    lateinit var edit_surname:EditText
    lateinit var edit_phone:EditText
    lateinit var edit_photo_url:EditText
    lateinit var btn_save:Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFieldsAndButton(view)

    }
    fun initFieldsAndButton(view: View){
        edit_name = view.findViewById(R.id.editName)
        edit_surname = view.findViewById(R.id.edit_surname)
        edit_phone = view.findViewById(R.id.edit_phone)
        edit_photo_url = view.findViewById(R.id.edit_photo_url)
        btn_save = view.findViewById(R.id.button_save)
        val id_contact = arguments?.getInt(Constants.ID_KEY) ?: 0
        val getContactById = RepositoryContacts.getContact(id_contact)
            ?: Contact(0,"","","","")
        edit_name.setText(getContactById.name)
        edit_surname.setText(getContactById.surname)
        edit_phone.setText(getContactById.phone_number)
        edit_photo_url.setText(getContactById.photo_url)
        init_button_save_add_click_listener(id_contact)
    }
    fun init_button_save_add_click_listener(id_contact:Int){
        btn_save.setOnClickListener {
            updateContact(id_contact)
            val smallestScreenDp = requireActivity().resources.configuration.smallestScreenWidthDp
            if (smallestScreenDp>Constants.MIN_DP_FOR_TABLE){
                startFragmentListContactFromTableMode()
            }else{
                settingForPortaitAndLansdscapeForPhone()
            }
        }
    }
    fun updateContact(id_contact:Int){
        val contactEdited = Contact(id_contact,edit_name.text.toString(),
            edit_surname.text.toString(),edit_phone.text.toString(),edit_photo_url.text.toString())
        RepositoryContacts.updateContact(contactEdited)
    }
    fun settingForPortaitAndLansdscapeForPhone(){
        val orientation = requireActivity().resources.configuration.orientation
        if (orientation==Constants.LANDSCAPE_ORIENTATION){
            startFragmentListContactFromLandscapeMode()
        }else{
            startFragmentListContactFromPortraitMode()
        }
    }
    fun startFragmentListContactFromTableMode(){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container3, Fragment())
            .commit()
        updateDataRecyclerOtherFragment()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    fun startFragmentListContactFromLandscapeMode(){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container2, Fragment())
            .commit()
        updateDataRecyclerOtherFragment()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    fun startFragmentListContactFromPortraitMode(){
        parentFragmentManager.popBackStack()
    }
    fun updateDataRecyclerOtherFragment(){
        val rvFromFragmentList = requireActivity().findViewById<View>(R.id.fragment_container)
        val rv = rvFromFragmentList.findViewById<RecyclerView>(R.id.recycler_view_for_list)
        val edit_text_search = rvFromFragmentList.findViewById<EditText>(R.id.edit_search)
        rv.adapter?.notifyDataSetChanged()
        update_if_search_enabled(edit_text_search)
    }
    fun update_if_search_enabled(search_edit:EditText){
        if (RepositoryContacts.get_search_enabled()==true){
            search_edit.setText(search_edit.text)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_for_edit_item, container, false)
    }

    companion object {
       @JvmStatic
        fun newInstance() = FragmentForEditItem()
    }
}