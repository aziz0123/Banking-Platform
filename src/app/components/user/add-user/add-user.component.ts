import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../user/service/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  users: User[] = [];
  user: User = new User();
  selectedEmail: string = '';
  editing: boolean = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
      error => {
        console.error('Error fetching users:', error);
      }
    );
  }

  // Select a user for editing
  selectUserForEditing(email: string | undefined) {
    if (email) {
      this.loadUserByEmail(email);
    }
  }

  // Fetch user details by email
  loadUserByEmail(email: string) {
    this.userService.getUserByEmail(email).subscribe(
      data => {
        this.user = data;
        this.editing = true; // Enable editing mode
      },
      error => {
        console.error('Error fetching user:', error);
      }
    );
  }

  updateUser() {
    if (this.user.email) {
      this.userService.updateUser(this.user.email, this.user).subscribe(
        response => {
          console.log('User updated successfully', response);
          this.loadUsers(); // Reload the user list
          this.user = new User(); // Clear the form
          this.editing = false; // Disable editing mode
        },
        error => {
          console.error('Error updating user:', error);
        }
      );
    }
  }

  // Delete a user by email
  deleteUser(email: string | undefined) {
    if (email) {
      this.userService.deleteUser(email).subscribe(
        () => {
          console.log('User deleted successfully');
          this.loadUsers(); // Reload the user list
        },
        error => {
          console.error('Error deleting user:', error);
        }
      );
    }
  }
}
