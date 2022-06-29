import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        LoginScreen()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
        AlbumsScreen()
    }
}

struct SplashScreen: View {
    private let viewModel: SplashViewModel = InjectionHelper.shared.splashViewModel
    var body: some View {
        ProgressView()
            .scaleEffect(2, anchor: .center)
            .onAppear {
                viewModel.isUserLoggedIn(completionHandler: { loggedIn, _ in
                    if loggedIn as! Bool {
                        // navigate to albums
                    } else {
                        // navigate to login
                    }
                })
            }
    }
}

struct LoginScreen: View {
    private let viewModel: LoginViewModel = InjectionHelper.shared.loginViewModel
    @State private var userList: [User] = []
    @State private var goToAlbumsScreen = false
    
    var body: some View {
        NavigationView {
            if userList.isEmpty {
                ProgressView().scaleEffect(2, anchor: .center)
            } else {
                ZStack {
                    List(userList, id: \.id) { user in
                        Text(user.email)
                            .onTapGesture {
                                viewModel.loginUser(userId: user.id)
                                goToAlbumsScreen = true
                            }
                    }.navigationTitle("Select an account")
                        .navigationBarTitleDisplayMode(.inline)
                    
                    NavigationLink(destination: AlbumsScreen().navigationBarBackButtonHidden(true), isActive: $goToAlbumsScreen) {
                        EmptyView()
                    }
                }
            }
        }.onAppear {
            viewModel.usersState.watch { users in
                guard let users = users else { return }
                self.userList = users.list
            }
        }
    }
}

struct AlbumsScreen: View {
    @Environment(\.presentationMode) var presentationMode
    private let viewModel: AlbumListViewModel = InjectionHelper.shared.albumsViewModel
    @State private var loggedUser: User = User.Companion().emptyObject()
    @State private var albums: [AlbumWithThumbnail] = []
    @State private var goToPhotosScreen = false
    @State private var selectedAlbumID: Int64 = 0
    
    private var items: [GridItem] {
      Array(repeating: .init(.adaptive(minimum: 120)), count: 2)
    }
    
    var body: some View {
        VStack {
            if albums.isEmpty || loggedUser.name.isEmpty {
                ProgressView().scaleEffect(2, anchor: .center)
            } else {
                List(albums, id: \.id) { album in
                    AlbumRow(album: album)
                        .onTapGesture {
                            selectedAlbumID = album.id
                            goToPhotosScreen = true
                        }
                }.navigationTitle("\(loggedUser.name)'s albums")
                    .navigationBarTitleDisplayMode(.inline)
                    .toolbar {
                        Button("Logout", action: {
                            albums = []
                            loggedUser = User.Companion().emptyObject()
                            viewModel.logout()
                            presentationMode.wrappedValue.dismiss()
                        })
                    }
                
                NavigationLink(destination: PhotosScreen(albumId: selectedAlbumID), isActive: $goToPhotosScreen, label: { EmptyView() })
            }
        }.onAppear {
            viewModel.doInitViewModel()
            viewModel.loggedUser.watch { user in
                guard let user = user else { return }
                loggedUser = user
            }
            viewModel.albums.watch { albums in
                guard let albums = albums else { return }
                self.albums = albums.list
            }
        }
    }
}

struct AlbumRow: View {
    var album: AlbumWithThumbnail
    var body: some View {
        HStack {
            if #available(iOS 15.0, *) {
                AsyncImage(url: URL(string: album.albumThumbnailUrl))
            }
            Text(album.title)
        }
    }
}

struct PhotosScreen: View {
    var albumId: Int64
    private let viewModel = InjectionHelper.shared.photosViewModel
    @State private var album = Album.Companion().emptyObject()
    @State private var photos: [Photo] = []
    @State private var viewPhoto = false
    
    var body: some View {
        List(photos, id: \.id) { photo in
            NavigationLink(destination: ViewPhotoScreen(photo: photo), isActive: $viewPhoto, label: { PhotoRow(photo: photo) })
                .onTapGesture {
                    viewPhoto = true
                }
        }.navigationTitle(album.title)
            .navigationBarTitleDisplayMode(.inline)
            .onAppear {
                print("Album ID passed to PhotosScreen ~> \(albumId)")
                viewModel.getPhotosForAlbum(albumId: albumId)
                viewModel.getAlbum(albumId: albumId)
                viewModel.album.watch { album in
                    guard let album = album else { return }
                    self.album = album
                }
                viewModel.photos.watch { photos in
                    guard let photos = photos else { return }
                    self.photos = photos.list
                }
            }
    }
}

struct PhotoRow: View {
    var photo: Photo
    var body: some View {
        HStack {
            if #available(iOS 15.0, *) {
                AsyncImage(url: URL(string: photo.thumbnailUrl))
            }
            Text(photo.title)
        }
    }
}

struct ViewPhotoScreen: View {
    var photo: Photo
    var body: some View {
        if #available(iOS 15.0, *) {
            AsyncImage(url: URL(string: photo.url))
        }
    }
}
