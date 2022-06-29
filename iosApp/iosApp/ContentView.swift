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
    @State var selectedUser: User?
    @State var pickerID = 0
    
    var body: some View {
        
        VStack {
            Picker(selection: $selectedUser, label: Text("Items: \(userList.count)")) {
                ForEach(userList, id: \.self) { user in
                    Text(user.email)
                }
            }
            .id(pickerID)
        }.onAppear {
            viewModel.usersState.watch { users in
                guard let users = users else { return }
                self.userList = users.list
                self.pickerID += 1
            }
        }
        
    }
}
