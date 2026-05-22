import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { UserListComponent } from './user/user-list/user-list.component';
@NgModule({
  declarations: [ ],
  imports: [BrowserModule,FormsModule,AppComponent,UserListComponent],
  providers: [provideHttpClient() ],
  bootstrap: [],
})
export class AppModule {}
