import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookCopyDetailsComponent } from './book-copy-details.component';

describe('BookCopyDetailsComponent', () => {
  let component: BookCopyDetailsComponent;
  let fixture: ComponentFixture<BookCopyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookCopyDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookCopyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
