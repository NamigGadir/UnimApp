package com.unimapp.profile.peers

import androidx.lifecycle.viewModelScope
import com.unimapp.core.BaseViewModel
import com.unimapp.uitoolkit.adapters.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeersViewModel : BaseViewModel<PeersState, Unit>() {

    fun getPeers(userId: String) {
        viewModelScope.launch {
            handleProgressBar.postValue(true)
            delay(1000)
            handleProgressBar.postValue(false)
            postState(PeersState.PeersList(list))
        }
    }

    private val list = listOf<UserInfo>(
        UserInfo(
            "123","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        ),
        UserInfo(
            "123","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        ),
        UserInfo(
            "123","Idrak Atakisili", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        ),
        UserInfo(
            "123","Shahmal Quliyev", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        ),
        UserInfo(
            "123","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        ),
        UserInfo(
            "123","Namig Qadirov", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Jeff_Bell_profile_photo.jpg", "Aytisnik", "Qafqaz University"
        )
    )

}

sealed class PeersState {
    data class PeersList(val list: List<UserInfo>) : PeersState()
}