import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        LoginScreen()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct LoginScreen: View {
    let viewModel = InjectionHelper.shared.loginViewModel
    @State private var userList = [User]()
    
    init() {
        viewModel.usersState.watch { users in
            if let users = users {
                userList = users.list
            }
        }
    }
    
    var body: some View {
        
        return List {
            Text("first")
            Text("second")
            Text("third")
        }
    }
}
