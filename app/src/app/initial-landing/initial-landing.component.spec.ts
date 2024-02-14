import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialLandingComponent } from './initial-landing.component';

describe('InitialLandingComponent', () => {
  let component: InitialLandingComponent;
  let fixture: ComponentFixture<InitialLandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InitialLandingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InitialLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
