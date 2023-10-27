package com.peterchege.jetpackcomposeplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.peterchege.jetpackcomposeplayground.di.AppModule
import com.peterchege.jetpackcomposeplayground.util.Screens
import com.peterchege.jetpackcomposeplayground.ui.screens.all_post.AllPostsScreen
import com.peterchege.jetpackcomposeplayground.ui.screens.all_post.AllPostsScreenViewModel
import com.peterchege.jetpackcomposeplayground.ui.screens.post.PostScreen
import com.peterchege.jetpackcomposeplayground.ui.screens.post.PostScreenViewModel
import com.peterchege.jetpackcomposeplayground.util.viewModelFactory

@ExperimentalComposeUiApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    appModule: AppModule
){
    NavHost(
        navController =navController,
        startDestination = Screens.ALL_POST_SCREEN,
    ){
        composable(
            route = Screens.ALL_POST_SCREEN,
        ){
            AllPostsScreen(
                viewModel = viewModel<AllPostsScreenViewModel>(
                    factory = viewModelFactory {
                        AllPostsScreenViewModel(
                            repository = appModule.postRepository,
                            database = appModule.database,
                        )
                    }
                ),
                navigateToPostScreen = {
                    navController.navigate(Screens.POST_SCREEN + "/${it}")
                }
            )
        }
        composable(
            route = Screens.POST_SCREEN + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ){
            val postId = it.arguments?.getString("postId")
                ?: throw IllegalStateException("Post data missing.")
            PostScreen(
                postId = postId,
                viewModel = viewModel<PostScreenViewModel>(
                    factory = viewModelFactory {
                        PostScreenViewModel(repository =  appModule.postRepository,
                            savedStateHandle = SavedStateHandle()
                        )
                    },
                ),

            )
        }
    }
}