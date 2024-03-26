import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValokuvatComponent } from './valokuvat.component';

describe('ValokuvatComponent', () => {
  let component: ValokuvatComponent;
  let fixture: ComponentFixture<ValokuvatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValokuvatComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ValokuvatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
