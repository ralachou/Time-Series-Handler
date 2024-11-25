import pandas as pd

# Load the data
df = pd.read_csv("Ticker_Data.csv")  # Replace with your file path

# Detect outliers where absolute value > 1
outliers = df[abs(df["Value"]) > 1]
outlier_dates = outliers["Date"].unique()

# Highlight dates with outliers
df["Outlier"] = abs(df["Value"]) > 1

# Function 1: Remediation using carry forward method
def carry_forward_remediation(dataframe):
    df_cf = dataframe.copy()
    for date in outlier_dates:
        for ticker in df["Ticker"].unique():
            for prop in df["Property"].unique():
                index = df_cf[(df_cf["Date"] == date) & 
                              (df_cf["Ticker"] == ticker) & 
                              (df_cf["Property"] == prop)].index
                if not index.empty:
                    prev_index = df_cf.index.get_loc(index[0]) - 1
                    if prev_index >= 0:
                        df_cf.loc[index, "Value"] = df_cf.loc[prev_index, "Value"]
    return df_cf

# Function 2: Remediation using linear interpolation
def linear_interpolation_remediation(dataframe):
    df_li = dataframe.copy()
    df_li["Value"] = pd.to_numeric(df_li["Value"], errors="coerce")
    df_li["Value"] = df_li["Value"].interpolate(method="linear")
    return df_li

# Apply the remediation methods
df_carry_forward = carry_forward_remediation(df)
df_linear_interp = linear_interpolation_remediation(df)

# Save the results
df_carry_forward.to_csv("Ticker_Data_Carry_Forward.csv", index=False)  # Replace with desired path
df_linear_interp.to_csv("Ticker_Data_Linear_Interpolation.csv", index=False)  # Replace with desired path

# Display information about outliers
print("Outliers detected on dates:", outlier_dates)
