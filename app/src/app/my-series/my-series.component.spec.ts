import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MySeriesComponent } from './my-series.component';

describe('MySeriesComponent', () => {
  let component: MySeriesComponent;
  let fixture: ComponentFixture<MySeriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MySeriesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MySeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
