import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        SplashScreen()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreen()
        LoginScreen()
        AlbumsScreen()
    }
}

struct SplashScreen: View {
    private let viewModel: SplashViewModel = InjectionHelper.shared.splashViewModel
    @State private var goToLoginScreen = false
    @State private var goToAlbumsScreen = false
    
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(destination: LoginScreen().navigationBarBackButtonHidden(true), isActive: $goToLoginScreen) {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                        .scaleEffect(2, anchor: .center)
                }
                NavigationLink(destination: AlbumsScreen().navigationBarBackButtonHidden(true), isActive: $goToAlbumsScreen) {
                    EmptyView()
                }
            }
        }.onAppear {
            viewModel.isUserLoggedIn { userLoggedIn, _ in
                guard let userLoggedIn = userLoggedIn else { return }
                if userLoggedIn as! Bool {
                    goToAlbumsScreen = true
                } else {
                    goToLoginScreen = true
                }
            }
        }.onDisappear {
            viewModel.clear()
        }
    }
}

struct LoginScreen: View {
    private let viewModel: LoginViewModel = InjectionHelper.shared.loginViewModel
    @State private var userList: [User] = []
    
    var body: some View {
        VStack {
            Text("Login to view your photos")
            List {
                ForEach(userList, id: \.self) { user in
                    Text(user.email).onTapGesture {
                        viewModel.loginUser(userId: user.id)
                    }
                }
            }
        }.onAppear {
            viewModel.usersState.watch { users in
                guard let users = users else { return }
                self.userList = users.list
            }
        }.onDisappear {
            viewModel.clear()
        }
    }
}

struct AlbumsScreen: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    private let viewModel: AlbumListViewModel = InjectionHelper.shared.albumsViewModel
    @State private var loggedUserName: String = ""
    var body: some View {
        VStack {
            HStack {
                Text(loggedUserName)
                Spacer()
                Button("Logout", action: {
                    viewModel.logout()
                    presentationMode.wrappedValue.dismiss()
                })
            }.padding(20)
        }.onAppear {
            viewModel.doInitViewModel()
            viewModel.loggedUser.watch { user in
                guard let user = user else { return }
                loggedUserName = user.name
            }
        }.onDisappear {
            viewModel.clear()
        }
    }
}
