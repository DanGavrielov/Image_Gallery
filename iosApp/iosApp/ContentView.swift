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
    private var userList: [User] = [.init(id: 123, name: "", username: "", email: "", address: .init(street: "", suite: "", city: "", zipcode: "", geo: .init(lat: "", lng: "")), phone: "", website: "", company: .init(name: "", catchPhrase: "", bs: ""))]
    
    init() {
        // Initialize data...
        
        // This is throwing the error "Escaping closure captures mutating 'self' parameter"...
//        viewModel.usersState.watch { users in
//            guard let users = users else { return }
//            self.userList = users.list
//        }
        
    }
    
    var body: some View {
        
        List {
            ForEach(userList, id: \.name) { name in
                Text("\(name)")
                
                // Here you can have a print statement wheneve you need to print something in the console.
                let _ = print("added")
            }
        }

    }
}
