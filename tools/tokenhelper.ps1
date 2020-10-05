param(
    [Parameter(Mandatory=$true)]
    [String]
    $TokenValue
)

[System.Environment]::SetEnvironmentVariable("MVSCTOKEN", $TokenValue, "User")
Write-Output "Success: Set Music-VisualizerV2 Environment Variable!"