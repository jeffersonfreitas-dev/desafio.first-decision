import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDialogDetailComponent } from './user-dialog-detail.component';

describe('UserDialogDetailComponent', () => {
  let component: UserDialogDetailComponent;
  let fixture: ComponentFixture<UserDialogDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserDialogDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserDialogDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
