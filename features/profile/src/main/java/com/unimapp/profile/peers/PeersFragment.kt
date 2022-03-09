package com.unimapp.profile.peers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.unimapp.common.extensions.asColorResource
import com.unimapp.common.extensions.dp
import com.unimapp.core.BaseFragment
import com.unimapp.profile.R
import com.unimapp.profile.databinding.FragmentPeersLayoutBinding
import com.unimapp.uitoolkit.adapters.UserInfo
import com.unimapp.uitoolkit.adapters.UserListAdapter
import com.unimapp.uitoolkit.extensions.addDivider

class PeersFragment : BaseFragment<PeersViewModel, FragmentPeersLayoutBinding, PeersState, Unit>() {

    override fun getViewModelClass() = PeersViewModel::class.java
    private lateinit var peersAdapter: UserListAdapter
    private val navArgs by navArgs<PeersFragmentArgs>()

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPeersLayoutBinding
        get() = FragmentPeersLayoutBinding::inflate

    override val onViewInit: FragmentPeersLayoutBinding.() -> Unit = {
        peersAdapter = UserListAdapter()
        peersList.adapter = peersAdapter
        peersList.addDivider(color = R.color.unim_divider_color.asColorResource(requireContext()), height = 1.dp)
        viewmodel.getPeers(navArgs.userId)
    }

    override fun onStateUpdate(state: PeersState) {
        when (state) {
            is PeersState.PeersList -> updatePeersList(state.list)
        }
    }

    private fun updatePeersList(list: List<UserInfo>) {
        peersAdapter.submitList(list)
    }

}