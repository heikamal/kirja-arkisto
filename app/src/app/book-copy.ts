import { Book } from "./book";

export interface BookCopy {
    id: number;
    name: string | null;
    edition: number;
    editionYear: number;
    book: Book;
    purchasePrice: number | null;
    purchaseDate: string | null;
    condition: number;
    description: string | null;
    saleDate: string | null;
    salePrice: number | null;
    bookshelfId: number;
    seriesId: number;
  }