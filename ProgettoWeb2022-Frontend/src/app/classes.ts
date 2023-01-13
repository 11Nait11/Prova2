
export class Ad{
  id!: number;
  title!: string;
  description!: string;
  user!: User;
  property!: number;
  price!: number;
  mq!: number;
  status!: string;
  city!: string;
  auction!: string
  images: Blob[] = [];
}

export class User{
  id!: number;
  name!: string;
  lastname!: string;
  age!: number;
  nickname!: string;
  password!: string;
  email!: string;
  role!: string;
  isBanned!: boolean;
  telephone!: string;
  state!: string;
  country!: string;
  address!: string;
  postalCode!: string;
}

export interface Property{
  id: number;
  type: string;
  mq: number;
  latitude: string;
  longitude: string;
  user : string;
  ad : number;
}

export class Review{
  id!: number;
  title!: string;
  description!: string;
  vote!: number;
  user!: User;
  ad!: Ad;
}

export class Auction{
  id!: number;
  endTime!: string;
  currentPrice!: number;
  winner!: string;
  ad!: number;
  numTips!: number;
}

