export interface Address {
  addressId?: number;                // PK (optional)
  customerAddressType?: string;      // nullable
  customerAddressValue?: string;     // nullable
  customerIdentifier: number;        // NOT NULL
  effectiveDate?: string;            // nullable
}