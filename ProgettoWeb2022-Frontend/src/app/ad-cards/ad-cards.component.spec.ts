import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdCardsComponent } from './ad-cards.component';

describe('AdCardsComponent', () => {
  let component: AdCardsComponent;
  let fixture: ComponentFixture<AdCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdCardsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
