import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [FormsModule,CommonModule], 
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  userForm: User = { id:0, name: '', email: '' };
  isEditing = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
        // Display an error message to the user, e.g., using a MatSnackBar
      }
    );
  }

  editUser(user: User): void {
  console.log('Editing user:', user);
  this.userForm = { ...user };
  this.isEditing = true;
}

saveUser(): void {
  console.log('Saving form:', this.userForm);

  if (this.isEditing) {
    if (this.userForm.id == null) {
      console.error('Update blocked: userForm.id is missing');
      return;
    }
    this.userService.updateUser(this.userForm).subscribe({
      next: () => { this.loadUsers(); this.resetForm(); },
      error: (e) => console.error('Error updating user:', e),
    });
  } else {
    const { id, ...payload } = this.userForm; // do not send id on add
    this.userService.addUser(payload as User).subscribe({
      next: () => { this.loadUsers(); this.resetForm(); },
      error: (e) => console.error('Error adding user:', e),
    });
  }
}

deleteUser(id: number | undefined): void {
  console.log('Deleting id:', id);
  if (id == null) {
    console.error('Delete blocked: id is missing');
    return;
  }
  this.userService.deleteUser(id).subscribe({
    next: () => {
      if (this.userForm?.id === id) this.resetForm();
      this.loadUsers();
    },
    error: (e) => console.error('Error deleting user:', e),
  });
}

resetForm(): void {
  this.userForm = { name: '', email: '' }; // no id
  this.isEditing = false;
}
}