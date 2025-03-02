
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Product {
  id: number;
  name: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8088/api/products';

  constructor(private http: HttpClient) {}

  list(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  save(product: Product): Observable<HttpResponse<void>> {
    return this.http.post<void>(this.apiUrl, product, { observe: 'response' });
  }

  update(product: Product): Observable<HttpResponse<void>> {
    return this.http.put<void>(this.apiUrl, product, { observe: 'response' });
  }

  delete(productId: number): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${productId}`, { observe: 'response' });
  }
  
  getProductId(productId: number): Observable<HttpResponse<void>> {
    return this.http.get<void>(`${this.apiUrl}/${productId}`, { observe: 'response' });
  }

}
