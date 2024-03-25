import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValokuvaComponent } from './valokuva.component';

describe('ValokuvaComponent', () => {
  let component: ValokuvaComponent;
  let fixture: ComponentFixture<ValokuvaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValokuvaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ValokuvaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
