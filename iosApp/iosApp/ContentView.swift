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
    
    
    private let viewModel: LoginViewModel = InjectionHelper.shared.loginViewModel
    
    @State private var userList: [User] = []
    
    
    var body: some View {
        
        List {
            ForEach(userList, id: \.name) { name in
                Text("\(name)")
            }
            
        }.onAppear {
            viewModel.usersState.watch { users in
                guard let users = users else { return }
                self.userList = users.list
            }
        }

    }
}
