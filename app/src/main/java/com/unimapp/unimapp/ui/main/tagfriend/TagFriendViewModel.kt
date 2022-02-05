package com.unimapp.unimapp.ui.main.tagfriend

import com.ingress.core.BaseViewModel
import com.unimapp.uitoolkit.dialogs.SimpleMultiSelectorBottomSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TagFriendViewModel @Inject constructor(

) : BaseViewModel<Unit, Unit>() {

    val taggedFriends by lazy { arrayListOf<SimpleMultiSelectorBottomSheet.Item>() }


}

