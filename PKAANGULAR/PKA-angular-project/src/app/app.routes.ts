import { Routes } from '@angular/router';
//import { UserListComponent } from './user/user-list/user-list.component';
import { DetailListComponent } from './customer/details/details-list/details-list.component';
import { ClassificationListComponent } from './customer/classification/classification-list/classification-list.component';
import { IdentityListComponent } from './customer/identity/identity-list/identity-list.component';
import { ContactListComponent } from './customer/contact/contact-list/contact-list.component';
import { AddressListComponent } from './customer/address/address-list/address-list.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
 // { path: 'users', component: UserListComponent },
  { path: 'details', component: DetailListComponent },
  { path: 'classification', component: ClassificationListComponent },
  { path: 'identity', component: IdentityListComponent },
  { path: 'contacts', component: ContactListComponent },
  { path: 'address', component: AddressListComponent },
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];