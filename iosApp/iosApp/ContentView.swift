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
    private var userList: [User] = []
    
    init() {
        // Initialize data...
    }
    
    var body: some View {
        
        List {
            ForEach(userList, id: \.name) { name in
                Text("\(name)")
            }
        }
        
        /*
        onChange(of: reloadsData) { newValue in
            print("on change")
            viewModel.usersState.watch { users in
                print(users)
                guard let users = users else { return }
                
                self.userList = users.list
            }
        }
         */
        
    }
}
